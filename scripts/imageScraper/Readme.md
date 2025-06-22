# 🧾 Croquette Scraper

A lightweight script to gather croquette-related data and images to support development and testing for the [Croqueteria](../README.md) project.

It consists of two parts:

- Extracting croquette entries from [Wikipedia](https://en.wikipedia.org/wiki/Croquette) with randomized enrichment
- Scraping croquette images from [Pixabay](https://pixabay.com/de/images/search/croquette/) using Selenium

---

## 📦 Requirements

Install the necessary Python dependencies:

```bash
pip install requests beautifulsoup4 selenium webdriver-manager
```

Firefox must be installed on your system for image scraping.

---

## ▶️ Usage

### Scrape Wikipedia Croquette Data

```bash
python croquette_scraper.py
```

Generates a JSON file with enriched croquette data.

### Scrape Croquette Images from Pixabay

```bash
python image_scraper.py
```

Downloads up to 50 croquette images and stores them in a JSON or local directory, depending on your implementation.

---

## 🛠️ Notes

- Internet access is required for both scripts
- Image scraping relies on Selenium and Firefox
- Output is intended for **development use only**
