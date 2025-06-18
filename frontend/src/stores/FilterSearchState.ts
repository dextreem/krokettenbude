import { create } from "zustand";
import {
  RetrieveAllCroquettesSortByEnum,
  RetrieveAllCroquettesSortDirectionEnum,
  type RetrieveAllCroquettesRequest,
} from "../api-client";
import { persist } from "zustand/middleware";

interface CroquetteFiltersStore {
  filters: RetrieveAllCroquettesRequest;
  setFilters: (filters: Partial<RetrieveAllCroquettesRequest>) => void;
  resetFilters: () => void;
}

const initialState: RetrieveAllCroquettesRequest = {
  country: undefined,
  nameContains: undefined,
  descriptionContains: undefined,
  crunchiness: [],
  spiciness: [],
  minAverageRating: undefined,
  vegan: undefined,
  form: undefined,
  sortBy: RetrieveAllCroquettesSortByEnum.CreatedAt,
  sortDirection: RetrieveAllCroquettesSortDirectionEnum.Desc,
};

export const useCroquetteFiltersStore = create<CroquetteFiltersStore>()(
  persist(
    (set) => ({
      filters: initialState,
      setFilters: (newFilters) =>
        set((state) => ({
          filters: { ...state.filters, ...newFilters },
        })),
      resetFilters: () => set({ filters: initialState }),
    }),
    {
      name: "croquette-filters", // key in localStorage
    }
  )
);
