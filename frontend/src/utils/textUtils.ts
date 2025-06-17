export function limitToWords(text: string, wordLimit: number = 50): string {
  const words = text.trim().split(/\s+/);
  if (words.length <= wordLimit) return text;

  return words.slice(0, wordLimit).join(" ") + "…";
}

export function getRandomDouble(min = 1, max = 5): number {
  const value = Math.random() * (max - min) + min;
  return Math.round(value * 10) / 10; // round to 1 decimal place
}
