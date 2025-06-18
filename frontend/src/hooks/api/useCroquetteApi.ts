import { useMutation, useQuery } from "@tanstack/react-query";
import { useApiEndpoints } from "./useApi";
import type { AddCroquetteRequest } from "../../api-client";
import { useNavigate } from "react-router-dom";
import { ROUTES } from "../../utils/constants";

export function useGetCroquettes() {
  const { croquetteApi } = useApiEndpoints();

  const { isLoading, data, error, refetch } = useQuery({
    queryKey: ["croquettes"], // TODO: add filter and searches
    queryFn: async () => await croquetteApi.retrieveAllCroquettes(),
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
