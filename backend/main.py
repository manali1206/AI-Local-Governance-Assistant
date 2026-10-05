from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from google import genai
from dotenv import load_dotenv
import os


# =========================================================
# LOAD ENVIRONMENT VARIABLES
# =========================================================

load_dotenv()

GEMINI_API_KEY = os.getenv("GEMINI_API_KEY")

if not GEMINI_API_KEY:
    print("WARNING: GEMINI_API_KEY is not configured.")
    gemini_client = None
else:
    print("Gemini API key loaded successfully.")
    gemini_client = genai.Client(api_key=GEMINI_API_KEY)


# =========================================================
# FASTAPI APP
# =========================================================

app = FastAPI(
    title="AI Local Governance Assistant",
    version="1.0.0",
    description="Backend API for local governance services"
)


# =========================================================
# REQUEST MODEL
# =========================================================

class ChatRequest(BaseModel):
    question: str


# =========================================================
# PREDEFINED ENGLISH ANSWERS
# =========================================================

predefined_answers = {

    "hello":
        "Hello! I am your Local Governance Assistant. How can I help you?",

    "hi":
        "Hello! I am your Local Governance Assistant. How can I help you?",

    "hey":
        "Hello! I am your Local Governance Assistant. How can I help you?",

    "help":
        "I can help you with local governance questions about water supply, roads, street lights, garbage, Gram Panchayat, Gram Sabha, government hospitals, scholarships and other public services.",

    "what is gram panchayat":
        "Gram Panchayat is the basic local government institution at the village level. It works for local development and provides basic public services to the village.",

    "what is a gram panchayat":
        "Gram Panchayat is the basic local government institution at the village level. It works for local development and provides basic public services to the village.",

    "what does gram panchayat do":
        "A Gram Panchayat manages local village services such as water supply, sanitation, roads, street lights and other basic public facilities.",

    "what are the functions of gram panchayat":
        "The main functions of a Gram Panchayat include sanitation, water supply, roads, street lights, public health and other basic village services.",

    "functions of gram panchayat":
        "The main functions of a Gram Panchayat include sanitation, water supply, roads, street lights, public health and other basic village services.",

    "who is sarpanch":
        "The Sarpanch is the elected head of the Gram Panchayat and helps lead local village administration and development activities.",

    "what does sarpanch do":
        "The Sarpanch is the elected head of the Gram Panchayat. The Sarpanch leads Gram Panchayat meetings and helps coordinate local development and public services.",

    "what are the duties of sarpanch":
        "The Sarpanch leads Gram Panchayat meetings and helps coordinate local development, public services and village administration.",

    "what is gram sabha":
        "Gram Sabha is a meeting of eligible voters of a village. It discusses local development, public services and other village matters.",

    "what is a gram sabha":
        "Gram Sabha is a meeting of eligible voters of a village. It discusses local development, public services and other village matters.",

    "there is no water supply in my area":
        "If there is no water supply in your area, please report the problem to the Gram Panchayat or the concerned local authority. Mention your exact location and clearly describe the water supply problem.",

    "no water supply":
        "If there is no water supply in your area, please report the problem to the Gram Panchayat or the concerned local authority. Mention your exact location and clearly describe the water supply problem.",

    "water is not coming":
        "If water is not coming, report the problem to the Gram Panchayat or concerned local authority. Provide your location and details about the water supply issue.",

    "water not coming":
        "If water is not coming, report the problem to the Gram Panchayat or concerned local authority. Provide your location and details about the water supply issue.",

    "water problem":
        "For a water supply problem, contact the Gram Panchayat or concerned local authority. Mention the affected area and explain the problem clearly.",

    "bad road":
        "A bad road should be reported to the Gram Panchayat or concerned local authority. Mention the exact location and describe the condition of the road.",

    "road is damaged":
        "If a road is damaged, report it to the Gram Panchayat or concerned local authority. Provide the exact location and details of the damage.",

    "there is a bad road in my area":
        "A bad road should be reported to the Gram Panchayat or concerned local authority. Mention the exact location and describe the condition of the road.",

    "road problem":
        "For a road problem, report it to the Gram Panchayat or concerned local authority. Mention the exact location and describe the problem clearly.",

    "street light is not working":
        "If a street light is not working, report it to the Gram Panchayat or concerned local authority. Mention the exact location of the street light.",

    "street light not working":
        "If a street light is not working, report it to the Gram Panchayat or concerned local authority. Mention the exact location of the street light.",

    "street light problem":
        "For a street light problem, contact the Gram Panchayat or concerned local authority and provide the exact location.",

    "garbage is not collected":
        "If garbage is not being collected, report the problem to the Gram Panchayat or concerned local authority. Mention the location and frequency of the problem.",

    "garbage problem":
        "For a garbage problem, contact the Gram Panchayat or concerned local authority and provide the exact location of the issue.",

    "garbage is not being collected":
        "If garbage is not being collected, report the problem to the Gram Panchayat or concerned local authority. Mention the location and frequency of the problem.",

    "where is government hospital":
        "You can find the nearest government hospital using the Nearby Services section of this application or by searching for government hospitals on Google Maps.",

    "where is the government hospital":
        "You can find the nearest government hospital using the Nearby Services section of this application or by searching for government hospitals on Google Maps.",

    "government hospital":
        "You can find the nearest government hospital using the Nearby Services section of this application or by searching for government hospitals on Google Maps.",

    "how can students get scholarship":
        "Students can check eligible government scholarships through official scholarship portals. They should verify eligibility and keep the required documents ready.",

    "how to get scholarship":
        "Students can check eligible government scholarships through official scholarship portals. They should verify eligibility and keep the required documents ready.",

    "student scholarship":
        "Students can check eligible government scholarships through official scholarship portals. They should verify eligibility and keep the required documents ready.",

    "how can farmers get government help":
        "Farmers can check eligible government schemes through official government portals or the concerned government office and apply with the required documents.",

    "government help for farmers":
        "Farmers can check eligible government schemes through official government portals or the concerned government office and apply with the required documents.",

    "farmer government scheme":
        "Farmers can check eligible government schemes through official government portals or the concerned government office and apply with the required documents.",

    "how to apply for government certificate":
        "You can apply for a government certificate through the relevant government office or official online portal. Required documents should be submitted with the application.",

    "how can i get a government certificate":
        "You can apply for a government certificate through the relevant government office or official online portal. Required documents should be submitted with the application.",

    "government certificate":
        "You can apply for a government certificate through the relevant government office or official online portal. Required documents should be submitted with the application.",

    "where is police station":
        "You can find the nearest police station using the Nearby Services section of this application or Google Maps.",

    "where is the police station":
        "You can find the nearest police station using the Nearby Services section of this application or Google Maps.",

    "where is government school":
        "You can find nearby government schools using the Nearby Services section of this application or Google Maps.",

    "where is the government school":
        "You can find nearby government schools using the Nearby Services section of this application or Google Maps."
}


# =========================================================
# MARATHI PREDEFINED ANSWERS
# =========================================================

marathi_answers = {

    "ग्रामपंचायत म्हणजे काय?":
        "ग्रामपंचायत ही गावाच्या स्थानिक प्रशासनाची मूलभूत संस्था आहे. "
        "ती गावातील पाणीपुरवठा, स्वच्छता, रस्ते, दिवाबत्ती आणि इतर "
        "स्थानिक सार्वजनिक सेवांशी संबंधित कामे करते.",

    "ग्रामपंचायतीची मुख्य कामे कोणती आहेत?":
        "ग्रामपंचायतीची मुख्य कामे म्हणजे स्वच्छता, पाणीपुरवठा, रस्ते, "
        "दिवाबत्ती, सार्वजनिक आरोग्य आणि गावातील मूलभूत सुविधा व्यवस्थापन.",

    "सरपंचाची कामे काय आहेत?":
        "सरपंच हा ग्रामपंचायतीचा प्रमुख असतो. तो ग्रामपंचायतीच्या "
        "बैठका आणि विकासकामांचे नेतृत्व करतो तसेच गावातील स्थानिक "
        "समस्या आणि विकासकामांवर लक्ष ठेवतो.",

    "ग्रामसभा म्हणजे काय?":
        "ग्रामसभा म्हणजे गावातील पात्र मतदारांची सभा. "
        "गावातील विकासकामे आणि स्थानिक विषयांवर चर्चा करण्यासाठी "
        "ग्रामसभा आयोजित केली जाते.",

    "गावात पाणीपुरवठा होत नसेल तर काय करावे?":
        "पाणीपुरवठ्याची समस्या असल्यास ग्रामपंचायत कार्यालयात "
        "तक्रार करावी. आवश्यक असल्यास संबंधित स्थानिक प्रशासनाकडे "
        "तक्रार नोंदवता येते.",

    "कचरा वेळेवर उचलला जात नसेल तर तक्रार कशी करावी?":
        "कचरा उचलला जात नसल्यास ग्रामपंचायत किंवा संबंधित स्थानिक "
        "प्रशासनाकडे तक्रार करावी. तक्रारीमध्ये ठिकाण आणि समस्येचे "
        "वर्णन द्यावे.",

    "सरकारी रुग्णालय कुठे आहे?":
        "जवळील सरकारी रुग्णालय शोधण्यासाठी Nearby Services मधील "
        "Government Hospitals पर्याय वापरा. Google Maps वरही "
        "जवळील सरकारी रुग्णालय शोधता येते.",

    "सरकारी हॉस्पिटल कुठे आहे?":
        "जवळील सरकारी हॉस्पिटल शोधण्यासाठी Nearby Services मधील "
        "Government Hospitals पर्याय वापरा.",

    "खराब रस्त्याची तक्रार कुठे करावी?":
        "खराब रस्त्याची तक्रार ग्रामपंचायत किंवा संबंधित स्थानिक "
        "प्रशासनाकडे नोंदवावी. तक्रारीमध्ये रस्त्याचे ठिकाण आणि "
        "समस्येचे वर्णन द्यावे.",

    "रस्त्यावरचा दिवा बंद असेल तर काय करावे?":
        "रस्त्यावरचा दिवा बंद असल्यास ग्रामपंचायत कार्यालयात "
        "तक्रार नोंदवावी. तक्रारीमध्ये दिव्याचे अचूक ठिकाण नमूद करावे.",

    "विद्यार्थ्यांसाठी शिष्यवृत्ती कशी मिळवावी?":
        "विद्यार्थी संबंधित सरकारी शिष्यवृत्ती पोर्टलवर पात्रता "
        "तपासून अर्ज करू शकतात. आवश्यक कागदपत्रे तयार ठेवावीत.",

    "शेतकऱ्यांना सरकारी मदत कशी मिळते?":
        "शेतकरी पात्र सरकारी योजनांची माहिती घेऊन संबंधित अधिकृत "
        "सरकारी पोर्टल किंवा कार्यालयामार्फत अर्ज करू शकतात.",

    "सरकारी प्रमाणपत्रासाठी अर्ज कसा करावा?":
        "सरकारी प्रमाणपत्रासाठी संबंधित सरकारी कार्यालय किंवा "
        "अधिकृत ऑनलाइन पोर्टलवर अर्ज करता येतो. आवश्यक कागदपत्रे "
        "अर्जासोबत सादर करावी."
}


# =========================================================
# HOME
# =========================================================

@app.get("/")
def home():
    return {
        "status": "success",
        "message": "AI Local Governance Assistant Backend is running"
    }


# =========================================================
# HEALTH CHECK
# =========================================================

@app.get("/health")
def health():
    return {
        "status": "ok"
    }


# =========================================================
# CHAT API
# =========================================================

@app.post("/chat")
def chat(request: ChatRequest):

    question = request.question.strip()

    if not question:
        raise HTTPException(
            status_code=400,
            detail="Question cannot be empty"
        )

    normalized_question = " ".join(question.split())
    question_lower = normalized_question.lower()


    # =====================================================
    # PREDEFINED EXACT ANSWERS
    # =====================================================

    if question_lower in predefined_answers:

        print("Using predefined English answer.")

        return {
            "status": "success",
            "answer": predefined_answers[question_lower]
        }


    # =====================================================
    # KEYWORD FALLBACK ANSWERS
    # =====================================================

    # Water
    if (
        "water supply" in question_lower
        or "no water" in question_lower
        or "water is not coming" in question_lower
        or "water not coming" in question_lower
    ):

        print("Using water keyword answer.")

        return {
            "status": "success",
            "answer": "If there is no water supply in your area, please report the problem to the Gram Panchayat or the concerned local authority. Mention your exact location and clearly describe the water supply problem."
        }


    # Street light
    if (
        "street light" in question_lower
        or "streetlight" in question_lower
    ):

        print("Using street light keyword answer.")

        return {
            "status": "success",
            "answer": "If a street light is not working, report it to the Gram Panchayat or concerned local authority. Mention the exact location of the street light."
        }


    # Road
    if (
        "bad road" in question_lower
        or "road is damaged" in question_lower
        or "road problem" in question_lower
        or "damaged road" in question_lower
    ):

        print("Using road keyword answer.")

        return {
            "status": "success",
            "answer": "If a road is damaged or in poor condition, report it to the Gram Panchayat or concerned local authority. Mention the exact location and describe the problem."
        }


    # Garbage
    if (
        "garbage" in question_lower
        or "waste collection" in question_lower
        or "garbage is not collected" in question_lower
    ):

        print("Using garbage keyword answer.")

        return {
            "status": "success",
            "answer": "If garbage is not being collected, report the problem to the Gram Panchayat or concerned local authority. Mention the exact location and describe the problem."
        }


    # =====================================================
    # MARATHI ANSWERS
    # =====================================================

    if normalized_question in marathi_answers:

        print("Using Marathi predefined answer.")

        return {
            "status": "success",
            "answer": marathi_answers[normalized_question]
        }


    # =====================================================
    # GEMINI API
    # =====================================================

    if gemini_client is None:

        return {
            "status": "error",
            "answer": "Gemini API key is not configured. Please check the .env file."
        }


    try:

        print("Question not found in predefined answers.")
        print("Sending question to Gemini...")

        governance_prompt = f"""
You are an AI Local Governance Assistant.

Your purpose is to help citizens understand local public services
and common governance procedures.

Answer the user's question in simple and clear English.

Topics you can help with include:
- Gram Panchayat
- Gram Sabha
- Roads
- Water supply
- Street lights
- Garbage and sanitation
- Government hospitals
- Government schools
- Scholarships
- Government certificates
- Farmer welfare schemes
- Public grievances
- Local government services

Guidelines:
1. Give practical and easy-to-understand information.
2. Do not invent specific government rules, fees, deadlines,
   phone numbers, office addresses, or scheme eligibility requirements.
3. If the exact local authority is uncertain, say
   "concerned local authority" rather than inventing an office.
4. If a question requires current or location-specific information,
   advise the user to verify it with the relevant official government
   office or official government portal.
5. Keep the answer concise.
6. Do not claim that a complaint or application has been officially submitted.
7. If the question is unrelated to local governance, politely explain
   that you are designed primarily for local governance services.

User question:
{question}
"""

        response = gemini_client.models.generate_content(
            model="gemini-3.5-flash-lite",
            contents=governance_prompt
        )

        answer = response.text

        return {
            "status": "success",
            "answer": answer
        }


    except Exception as e:

        error_message = str(e)

        print("Gemini Error:", error_message)


        if "429" in error_message or "RESOURCE_EXHAUSTED" in error_message:

            return {
                "status": "error",
                "answer": "AI service quota exceeded. Please try again later."
            }


        if "503" in error_message or "UNAVAILABLE" in error_message:

            return {
                "status": "error",
                "answer": "AI service is temporarily busy. Please try again later."
            }


        if "401" in error_message or "403" in error_message:

            return {
                "status": "error",
                "answer": "Gemini API authentication failed. Please check the API key."
            }


        return {
            "status": "error",
            "answer": "AI service error. Please try again later."
        }

        # =====================================================
# WEEK 6 - AI/NLP API
# =====================================================

@app.post("/api/ask")
def ask_ai(request: ChatRequest):

    return chat(request)