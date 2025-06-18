export function getCroquetteFormEmoji(form: string): string {
  switch (form.toLowerCase()) {
    case "cylindric":
      return "🥖";
    case "oval":
      return "🥟";
    case "ball":
      return "🧆";
    case "disk":
      return "🍘";
    case "other":
    default:
      return "🍽️";
  }
}
