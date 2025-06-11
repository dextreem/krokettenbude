import requests
from bs4 import BeautifulSoup, NavigableString, Tag
import json
import random

url = "https://en.wikipedia.org/wiki/Croquette"
response = requests.get(url)
soup = BeautifulSoup(response.content, "html.parser")

forms = ["cylindric", "disk", "oval", "ball"]
result = []
result_json = "croquette_countries.json"

def clean_paragraph_text(p_tag):
    """Extracts clean paragraph text while preserving spacing around <a> tags and removing footnotes."""
    for sup in p_tag.find_all("sup"):
        sup.decompose()

    parts = []
    for elem in p_tag.contents:
        if isinstance(elem, NavigableString):
            parts.append(str(elem))
        elif isinstance(elem, Tag):
            text = elem.get_text(strip=True)
            if parts and not parts[-1].endswith((" ", "(", "-", "—")):
                parts.append(" ")
            parts.append(text)
    return "".join(parts).strip()

for heading_div in soup.select("div.mw-heading.mw-heading3"):
    h3 = heading_div.find("h3")
    if not h3:
        continue

    country = h3.get_text(strip=True)

    next_elem = heading_div.find_next_sibling()
    description = ""

    while next_elem:
        if next_elem.name == "p":
            description = clean_paragraph_text(next_elem)
            break
        elif next_elem.name and next_elem.name.startswith("h"):
            break
        next_elem = next_elem.find_next_sibling()

    if description:
        result.append({
            "country": country,
            "description": description,
            "crunchiness": random.randint(1, 5),
            "spiciness": random.randint(1, 5),
            "vegan": random.choice([True, False]),
            "form": random.choice(forms)
        })

with open(result_json, "w", encoding="utf-8") as f:
    json.dump(result, f, ensure_ascii=False, indent=2)

print(f"{url} scraped and written to {result_json}.")
