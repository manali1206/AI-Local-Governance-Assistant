import os

from dotenv import load_dotenv
from fastapi import FastAPI, HTTPException, Header, Depends
from fastapi.security import HTTPBearer, HTTPAuthorizationCredentials
from pydantic import BaseModel
from supabase import create_client, Client

BASE_DIR = os.path.dirname(os.path.abspath(__file__))
ENV_FILE = os.path.join(BASE_DIR, ".env")

load_dotenv(ENV_FILE)



SUPABASE_URL = os.getenv("SUPABASE_URL")
SUPABASE_SECRET_KEY = os.getenv("SUPABASE_SECRET_KEY")

ADMIN_EMAILS = [
    email.strip().lower()
    for email in os.getenv("ADMIN_EMAILS", "").split(",")
    if email.strip()
]

if not SUPABASE_URL or not SUPABASE_SECRET_KEY:
    raise RuntimeError("Supabase environment variables are missing")

supabase: Client = create_client(
    SUPABASE_URL,
    SUPABASE_SECRET_KEY
)

app = FastAPI()
security = HTTPBearer()

def verify_admin(authorization: str | None):
    if not authorization:
        raise HTTPException(
            status_code=401,
            detail="Authorization header is required"
        )

    if not authorization.startswith("Bearer "):
        raise HTTPException(
            status_code=401,
            detail="Invalid authorization header"
        )

    token = authorization.replace("Bearer ", "", 1).strip()

    try:
        user_response = supabase.auth.get_user(token)
        user = user_response.user

        if not user or not user.email:
            raise HTTPException(
                status_code=401,
                detail="Invalid authentication token"
            )

        if user.email.lower() not in ADMIN_EMAILS:
            raise HTTPException(
                status_code=403,
                detail="Admin access required"
            )

        return user

    except HTTPException:
        raise

    except Exception:
        raise HTTPException(
            status_code=401,
            detail="Invalid authentication token"
        )

class StatusUpdateRequest(BaseModel):
    status: str


@app.get("/")
def home():
    return {
        "message": "AI Local Governance Assistant Backend is running"
    }


@app.get("/health")
def health_check():
    return {
        "status": "ok"
    }


@app.get("/test-supabase")
def test_supabase():
    try:
        response = (
            supabase
            .from_("Grievances")
            .select("id")
            .limit(1)
            .execute()
        )

        return {
            "status": "success",
            "message": "Backend connected to Supabase successfully"
        }

    except Exception as e:
        raise HTTPException(
            status_code=500,
            detail=f"Supabase connection failed: {str(e)}"
        )

class StatusUpdateRequest(BaseModel):
    status: str


@app.patch("/grievances/{grievance_id}/status")
def update_grievance_status(
    grievance_id: str,
    request: StatusUpdateRequest,
    credentials: HTTPAuthorizationCredentials = Depends(security)
):
    verify_admin(f"Bearer {credentials.credentials}")
    allowed_statuses = [
        "Pending",
        "In Progress",
        "Resolved",
        "Rejected"
    ]

    if request.status not in allowed_statuses:
        raise HTTPException(
            status_code=400,
            detail="Invalid grievance status"
        )

    try:
        grievance_response = (
            supabase
            .from_("Grievances")
            .select("id, status")
            .eq("id", grievance_id)
            .single()
            .execute()
        )

        grievance = grievance_response.data

        if not grievance:
            raise HTTPException(
                status_code=404,
                detail="Grievance not found"
            )

        old_status = grievance["status"]

        if old_status == request.status:
            return {
                "status": "success",
                "message": "Status is already set to this value"
            }

        supabase \
            .from_("Grievances") \
            .update({
                "status": request.status
            }) \
            .eq("id", grievance_id) \
            .execute()

        supabase \
            .from_("grievance_status_history") \
            .insert({
                "grievance_id": grievance_id,
                "old_status": old_status,
                "new_status": request.status
            }) \
            .execute()

        return {
            "status": "success",
            "message": "Grievance status updated successfully",
            "old_status": old_status,
            "new_status": request.status
        }

    except HTTPException:
        raise

    except Exception as e:
        raise HTTPException(
            status_code=500,
            detail=f"Failed to update grievance status: {str(e)}"
        ) 