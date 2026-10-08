import os

from dotenv import load_dotenv
from fastapi import FastAPI, HTTPException, Depends
from fastapi.middleware.cors import CORSMiddleware
from fastapi.security import HTTPBearer, HTTPAuthorizationCredentials
from pydantic import BaseModel
from supabase import create_client, Client
from datetime import datetime, timezone


# =============================
# ENVIRONMENT
# =============================

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
    raise RuntimeError(
        "Supabase environment variables are missing"
    )


supabase: Client = create_client(
    SUPABASE_URL,
    SUPABASE_SECRET_KEY
)


# =============================
# FASTAPI APP
# =============================

app = FastAPI()


app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=False,
    allow_methods=["*"],
    allow_headers=["*"],
)


# =============================
# AUTHENTICATION
# =============================

security = HTTPBearer()


def verify_admin(
    credentials: HTTPAuthorizationCredentials = Depends(security)
):
    token = credentials.credentials

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

    except Exception as error:

        print(
            "AUTHENTICATION ERROR:",
            repr(error)
        )

        raise HTTPException(
            status_code=401,
            detail="Invalid authentication token"
        )


# =============================
# REQUEST MODELS
# =============================

class StatusUpdateRequest(BaseModel):
    status: str


# =============================
# BASIC ROUTES
# =============================

@app.get("/")
def home():

    return {
        "message":
        "AI Local Governance Assistant Backend is running"
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
            "message":
            "Backend connected to Supabase successfully"
        }

    except Exception as error:

        raise HTTPException(
            status_code=500,
            detail=
            f"Supabase connection failed: {str(error)}"
        )


# =============================
# ADMIN DASHBOARD
# =============================

@app.get("/admin/dashboard")
def admin_dashboard(
    user=Depends(verify_admin)
):

    try:

        response = (
            supabase
            .from_("Grievances")
            .select("status")
            .execute()
        )

        grievances = response.data or []

        total = len(grievances)

        pending = sum(
            1
            for grievance in grievances
            if grievance.get("status") == "Pending"
        )

        in_progress = sum(
            1
            for grievance in grievances
            if grievance.get("status") == "In Progress"
        )

        resolved = sum(
            1
            for grievance in grievances
            if grievance.get("status") == "Resolved"
        )

        rejected = sum(
            1
            for grievance in grievances
            if grievance.get("status") == "Rejected"
        )

        return {
            "status": "success",
            "total": total,
            "pending": pending,
            "in_progress": in_progress,
            "resolved": resolved,
            "rejected": rejected
        }

    except Exception as error:

        raise HTTPException(
            status_code=500,
            detail=
            f"Failed to load dashboard statistics: {str(error)}"
        )


# =============================
# ADMIN GRIEVANCES
# =============================

@app.get("/admin/grievances")
def admin_grievances(
    user=Depends(verify_admin)
):

    try:

        response = (
            supabase
            .from_("Grievances")
<<<<<<< HEAD
            .select(
                "id,reference_id,title,status,created_at"
            )
=======
            .select("id, reference_id, title, status, created_at")
>>>>>>> 3bbcbbf (fix: complete admin grievance management)
            .execute()
        )

        return {
            "status": "success",
            "grievances": response.data or []
        }

    except Exception as error:

        raise HTTPException(
            status_code=500,
            detail=
            f"Failed to load grievances: {str(error)}"
        )


# =============================
# ADMIN GRIEVANCE DETAILS
# =============================

@app.get("/admin/grievances/{grievance_id}")
def get_admin_grievance(
    grievance_id: str,
    user=Depends(verify_admin)
):

    try:

        response = (
            supabase
            .table("Grievances")
            .select(
                "id,reference_id,title,description,"
                "user_id,status,created_at"
            )
            .eq("id", grievance_id)
            .single()
            .execute()
        )

        if not response.data:

            raise HTTPException(
                status_code=404,
                detail="Grievance not found"
            )

        return {
            "status": "success",
            "grievance": response.data
        }

    except HTTPException:
        raise

    except Exception as error:

        raise HTTPException(
            status_code=500,
            detail=
            f"Failed to load grievance: {str(error)}"
        )


# =============================
# STATUS HISTORY
# =============================

@app.get(
    "/admin/grievances/{grievance_id}/history"
)
def grievance_status_history(
    grievance_id: str,
    user=Depends(verify_admin)
):

    try:

        response = (
            supabase
            .from_("grievance_status_history")
            .select("*")
            .eq("grievance_id", grievance_id)
            .execute()
        )

        return {
            "status": "success",
            "history": response.data or []
        }

    except Exception as error:

        raise HTTPException(
            status_code=500,
            detail=
            f"Failed to load grievance history: {str(error)}"
        )


# =============================
# UPDATE GRIEVANCE STATUS
# =============================

@app.patch(
    "/grievances/{grievance_id}/status"
)
def update_grievance_status(
    grievance_id: str,
    request: StatusUpdateRequest,
    user=Depends(verify_admin)
):

    allowed_statuses = [
        "Pending",
        "In Progress",
        "Resolved",
        "Rejected"
    ]


    if request.status not in allowed_statuses:

        raise HTTPException(
            status_code=400,
            detail="Invalid status"
        )


    try:

        # =============================
        # GET CURRENT GRIEVANCE
        # =============================

        current_response = (
            supabase
            .table("Grievances")
            .select("*")
            .eq("id", grievance_id)
            .single()
            .execute()
        )

        current_data = current_response.data


        if not current_data:

            raise HTTPException(
                status_code=404,
                detail="Grievance not found"
            )


        old_status = current_data.get("status")


        # =============================
        # UPDATE GRIEVANCE
        # =============================

        update_response = (
            supabase
            .table("Grievances")
            .update({
                "status": request.status
            })
            .eq("id", grievance_id)
            .execute()
        )


        print(
            "STATUS UPDATE RESULT:",
            update_response.data
        )


        if not update_response.data:

            raise HTTPException(
                status_code=500,
                detail=
                "Grievance status was not updated"
            )


        # =============================
        # INSERT STATUS HISTORY
        # =============================

        history_response = (
            supabase
            .table("grievance_status_history")
            .insert({
                "grievance_id": grievance_id,
                "old_status": old_status,
                "new_status": request.status,
                "changed_at":
                    datetime.now(
                        timezone.utc
                    ).isoformat()
            })
            .execute()
        )


        print(
            "HISTORY INSERT RESULT:",
            history_response.data
        )


        # =============================
        # GET UPDATED GRIEVANCE
        # =============================

        updated_response = (
            supabase
            .table("Grievances")
            .select("*")
            .eq("id", grievance_id)
            .single()
            .execute()
        )


        print(
            "UPDATED GRIEVANCE:",
            updated_response.data
        )


        return {
            "message":
                "Status updated successfully",

            "grievance":
                updated_response.data,

            "history":
                history_response.data
        }


    except HTTPException:
        raise


    except Exception as error:

        print(
            "STATUS UPDATE ERROR:",
            repr(error)
        )

        raise HTTPException(
            status_code=500,
            detail=str(error)
        )