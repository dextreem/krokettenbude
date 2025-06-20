import { create } from "zustand";
import {
  RetrieveAllCroquettesFormEnum,
  RetrieveAllCroquettesSortByEnum,
  RetrieveAllCroquettesSortDirectionEnum,
  type RetrieveAllCroquettesRequest,
} from "../api-client";
import { persist } from "zustand/middleware";

interface CroquetteFiltersStore {
  filters: RetrieveAllCroquettesRequest;
  useFilters: boolean;
  setFilters: (filters: Partial<RetrieveAllCroquettesRequest>) => void;
  resetFilters: () => void;
  toggleUseFilters: () => void;
}

const initialState: RetrieveAllCroquettesRequest = {
  country: undefined,
  nameContains: undefined,
  descriptionContains: undefined,
  crunchiness: [1, 2, 3, 4, 5],
  spiciness: [1, 2, 3, 4, 5],
  minAverageRating: undefined,
  vegan: false,
  form: RetrieveAllCroquettesFormEnum.Cylindric,
  sortBy: RetrieveAllCroquettesSortByEnum.CreatedAt,
  sortDirection: RetrieveAllCroquettesSortDirectionEnum.Desc,
};

export const useCroquetteFiltersStore = create<CroquetteFiltersStore>()(
  persist(
    (set) => ({
      filters: initialState,
      useFilters: false,
      setFilters: (newFilters) =>
        set((state) => ({
          filters: { ...state.filters, ...newFilters },
        })),
      resetFilters: () =>
        set((state) => ({
          filters: {
            ...initialState,
            sortBy: state.filters.sortBy,
            sortDirection: state.filters.sortDirection,
          },
        })),
      toggleUseFilters: () =>
        set((state) => ({ useFilters: !state.useFilters })),
    }),
    {
      name: "croquette-filters", // key in localStorage
    }
  )
);
