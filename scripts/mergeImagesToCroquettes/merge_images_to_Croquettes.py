import json
import random

with open("../imageScraper/croquette_images.json", "r", encoding="utf-8") as f:
    images = json.load(f)

with open("../croquetteScraper/croquette_countries_refined.json", "r", encoding="utf-8") as f:
    croquettes = json.load(f)

# Assign random images to each croquette entry, overwriting "imageUrl"
for item in croquettes:
    item["imageUrl"] = random.choice(images)

# Save the updated croquette data
with open("croquette_data_with_images.json", "w", encoding="utf-8") as f:
    json.dump(croquettes, f, indent=2, ensure_ascii=False)

print(f"Updated {len(croquettes)} items with random images and saved to croquette_data_with_images.json")
