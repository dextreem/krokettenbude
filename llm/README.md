# 🧠 Small Local LLM for Recommendation System

This module leverages `deepseek-r1:1.5b`, a compact language model suitable for running directly on laptops. It's used to interpret user preferences for croquettes in a structured format.

---

## 🏗️ How to Build

Prepare and run the local Ollama container:

```bash
podman build -t localhost/deepseek-local .
podman run -d --name ollama-deepseek -p 11434:11434 localhost/deepseek-local
```

Ensure `podman` is installed and configured properly.

---

## 🚀 How to Use

### JSON Response Format

Query the LLM to return structured JSON data:

```bash
curl http://localhost:11434/api/generate -d '{
  "model": "deepseek-r1:1.5b",
  "prompt": "Analyze this text and return its content in this form (Return only the JSON, nothing else, no explanation!):\n{\"preferredSpiciness\": 1-5,\"preferredCrunchiness\": 1-5,\"form\": \"CYLINDRIC or BALL or DISC or OTHER\",\"vegan\": true or false}\n\nI like my croquettes rather soft and a bit hot. Im for the classic form of a croquette.",
  "stream": false
}'
```

### Plain Text Format

Query the LLM to return the values in plain text:

```bash
curl http://localhost:11434/api/generate -d '{
  "model": "deepseek-r1:1.5b",
  "prompt": "Analyze this text and find out which values are required for preferredSpiciness (number from 1 to 5), preferredCrunchiness (number from 1-5), vegan (true or false), and form (CYLINDRIC, BALL, DISK or OTHER). Return only preferredSpiciness, preferredCrunchiness, vegan, and form along with their values, no explanation.\n\nThis is the text: I like my croquettes rather soft and a bit hot. Im for the classic form of a croquette.",
  "stream": false
}'
```

---

## 📝 Notes

- Designed for local development use
- Lightweight model suitable for machines without GPUs
- Ideal for structured preference extraction from user input
