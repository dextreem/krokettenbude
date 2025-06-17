from selenium import webdriver
from selenium.webdriver.firefox.service import Service
from selenium.webdriver.firefox.options import Options
from webdriver_manager.firefox import GeckoDriverManager
from selenium.webdriver.common.by import By
import time
import json

# Firefox options (headless mode)
options = Options()
options.headless = True  # Run Firefox without opening a window

# Setup Firefox driver service
service = Service(GeckoDriverManager().install())

# Initialize Firefox driver with service and options
driver = webdriver.Firefox(service=service, options=options)

try:
    driver.get("https://pixabay.com/de/images/search/croquette/")
    time.sleep(3)

    # Scroll to load more images (optional)
    for _ in range(3):
        driver.execute_script("window.scrollTo(0, document.body.scrollHeight);")
        time.sleep(2)

    images = driver.find_elements(By.CSS_SELECTOR, "img[srcset]")

    image_urls = []
    for img in images:
        srcset = img.get_attribute("srcset")
        if srcset:
            # Take highest-res URL from srcset attribute
            high_res_url = srcset.split(",")[-1].strip().split()[0]
            if high_res_url.startswith("https://cdn.pixabay.com"):
                image_urls.append(high_res_url)

    # Deduplicate and limit to 50 images
    image_urls = list(set(image_urls))[:50]

    with open("croquette_images.json", "w", encoding="utf-8") as f:
        json.dump(image_urls, f, indent=2)

    print(f"Saved {len(image_urls)} image URLs to croquette_images.json")

finally:
    driver.quit()
