import { useMutation, useQuery } from "@tanstack/react-query";
import { useApiEndpoints } from "./useApi";
import type { AddCroquetteRequest } from "../../api-client";
import { useNavigate } from "react-router-dom";
import { ROUTES } from "../../utils/constants";
import { useCroquetteFiltersStore } from "../../stores/FilterSearchState";

export function useGetCroquettes() {
  const { croquetteApi } = useApiEndpoints();
  const { filters, useFilters } = useCroquetteFiltersStore();

  const searchFilters = {
    nameContains: filters.nameContains,
    descriptionContains: filters.descriptionContains,
    country: filters.country,
    sortBy: filters.sortBy,
    sortDirection: filters.sortDirection,
  };

  const { isLoading, data, error, refetch } = useQuery({
    queryKey: ["croquettes", useFilters ? filters : searchFilters],
    queryFn: async () =>
      useFilters
        ? await croquetteApi.retrieveAllCroquettes(filters)
        : await croquetteApi.retrieveAllCroquettes(searchFilters),
  });

  return {
    isCroquettesLoading: isLoading,
    croquettes: data,
    error,
    refetchCroquettes: refetch,
  };
}

export function useGetSingleCroquette(croquetteId: number) {
  const { croquetteApi } = useApiEndpoints();

  const { isLoading, data, error, refetch } = useQuery({
    queryKey: ["croquette", croquetteId],
    queryFn: async () =>
      await croquetteApi.retrieveCroquetteById({ croquetteId }),
  });

  return {
    isCroquetteLoading: isLoading,
    croquette: data,
    error,
    refetch,
  };
}

export function useCreateCroquette() {
  const navigate = useNavigate();
  const { croquetteApi } = useApiEndpoints();

  const postCroquetteMutation = useMutation({
    mutationFn: async (requestData: AddCroquetteRequest) =>
      await croquetteApi.addCroquette(requestData),
    onSuccess: () => {
      console.log("Croquette Created");
      navigate(`/${ROUTES.CROQUETTES}`);
    },
    onError: (error) => console.log("Create user failed:", error),
  });

  return {
    createCroquette: postCroquetteMutation.mutate,
    isCreatingCroquette: postCroquetteMutation.isPending,
    creationError: postCroquetteMutation.error,
  };
}
