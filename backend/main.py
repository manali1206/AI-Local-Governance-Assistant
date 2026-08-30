from fastapi import FastAPI
from pydantic import BaseModel

app = FastAPI()


class ChatRequest(BaseModel):
    message: str


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


@app.post("/chat")
def chat(request: ChatRequest):
    return {
        "response": f"Backend received: {request.message}"
    }