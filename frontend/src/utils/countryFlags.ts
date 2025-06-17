const countryToFlag: Record<string, string> = {
  France: "🇫🇷",
  Belgium: "🇧🇪",
  "Germany, Austria, and Switzerland": "🇩🇪🇦🇹🇨🇭",
  Hungary: "🇭🇺",
  Italy: "🇮🇹",
  Ireland: "🇮🇪",
  "The Netherlands": "🇳🇱",
  Poland: "🇵🇱",
  Portugal: "🇵🇹",
  Russia: "🇷🇺",
  Spain: "🇪🇸",
  "United Kingdom": "🇬🇧",
  India: "🇮🇳",
  "Sri Lanka": "🇱🇰",
  China: "🇨🇳",
  Indonesia: "🇮🇩",
  Japan: "🇯🇵",
  "South Korea": "🇰🇷",
  "Puerto Rico": "🇵🇷",
  Cuba: "🇨🇺",
  "Dominican Republic": "🇩🇴",
  Aruba: "🇦🇼",
  Mexico: "🇲🇽",
  "United States": "🇺🇸",
  Brazil: "🇧🇷",
  Ecuador: "🇪🇨",
  Uruguay: "🇺🇾",
  Colombia: "🇨🇴",
};

export function getFlag(country: string): string {
  return countryToFlag[country] ?? "🏳️";
}
