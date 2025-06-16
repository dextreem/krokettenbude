// stores/sessionState.ts
import { create } from "zustand";
import { devtools, persist } from "zustand/middleware";

interface UserDetails {
  email: string;
  // TODO: Use proper User Details
}

// Define the shape of the entire store
interface SessionState {
  token: string | null;
  userDetails: UserDetails | null;

  setToken: (token: string) => void;
  setUserDetails: (userDetails: UserDetails) => void;
  resetSession: () => void;
}

const useSessionState = create<SessionState>()(
  devtools(
    persist(
      (set) => ({
        token: null,
        userDetails: null,

        setToken: (token) => set({ token }),
        setUserDetails: (userDetails) => set({ userDetails }),
        resetSession: () => set({ token: null, userDetails: null }),
      }),
      { name: "sessionState" }
    )
  )
);

export default useSessionState;
