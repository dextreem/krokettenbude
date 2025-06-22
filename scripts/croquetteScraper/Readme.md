# 🧾 Croquette Scraper

A lightweight script to extract croquette entries from [Wikipedia](https://en.wikipedia.org/wiki/Croquette), enrich them with randomized data, and save them to a JSON file.

This tool is designed to generate realistic test data for the [Croqueteria](../README.md) project.

---

## 📦 Requirements

Install the necessary Python dependencies:

```bash
pip install requests beautifulsoup4
```

---

## ▶️ Usage

Simply run the script:

```bash
python croquette_scraper.py
```

The script will output a JSON file containing enriched croquette data suitable for development and testing.

---

## 🛠️ Notes

- Data is semi-random and meant for **development use only**
- Internet access is required to fetch data from Wikipedia
