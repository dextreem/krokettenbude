export function limitToWords(text: string, wordLimit: number = 50): string {
  const words = text.trim().split(/\s+/);
  if (words.length <= wordLimit) return text;

  return words.slice(0, wordLimit).join(" ") + "…";
}

export function getRandomDouble(min = 1, max = 5): number {
  const value = Math.random() * (max - min) + min;
  return Math.round(value * 10) / 10; // round to 1 decimal place
}

export function getRelativeTime(date: Date): string {
  const rtf = new Intl.RelativeTimeFormat("en", { numeric: "auto" });
  const now = new Date();
  const diff = +now - +date;

  const seconds = Math.floor(diff / 1000);
  const minutes = Math.floor(diff / (1000 * 60));
  const hours = Math.floor(diff / (1000 * 60 * 60));
  const days = Math.floor(diff / (1000 * 60 * 60 * 24));
  const months = Math.floor(days / 30);
  const years = Math.floor(days / 365);

  if (seconds < 60) return rtf.format(-seconds, "second");
  if (minutes < 60) return rtf.format(-minutes, "minute");
  if (hours < 24) return rtf.format(-hours, "hour");
  if (days < 30) return rtf.format(-days, "day");
  if (months < 12) return rtf.format(-months, "month");
  return rtf.format(-years, "year");
}
