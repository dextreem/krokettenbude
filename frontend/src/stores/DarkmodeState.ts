import { create } from "zustand";
import { persist } from "zustand/middleware";

interface DarkModeState {
  isDarkMode: boolean;
  toggleDarkMode: () => void;
}

const useDarkModeState = create<DarkModeState>()(
  persist(
    (set) => ({
      isDarkMode: false,
      toggleDarkMode: () => set((state) => ({ isDarkMode: !state.isDarkMode })),
    }),
    {
      name: "dark-mode",
    }
  )
);

export default useDarkModeState;
