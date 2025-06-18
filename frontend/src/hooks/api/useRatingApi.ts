import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { useApiEndpoints } from "./useApi";
import type { AddRatingRequest, UpdateRatingRequest } from "../../api-client";
import toast from "react-hot-toast";

export function useGetUserRatingForCroquette(croquetteId: number) {
  const { ratingApi } = useApiEndpoints();

  const { isLoading, data, error, refetch } = useQuery({
    queryKey: ["rating", croquetteId],
    queryFn: async () =>
      await ratingApi.retrieveRatingForCroquetteId({ croquetteId }),
    retry: false, // 404 is okay, don't retry
  });

  return {
    isRatingLoading: isLoading,
    rating: data,
    error,
    refetchRating: refetch,
  };
}

export function useCreateRating() {
  const { ratingApi } = useApiEndpoints();
  const queryClient = useQueryClient();

  const postRatingMutation = useMutation({
    mutationFn: async (requestData: AddRatingRequest) =>
      await ratingApi.addRating(requestData),
    onSuccess: () => {
      toast.dismiss();
      toast.success("Thanks for your rating. You are awesome!");
      queryClient.invalidateQueries({ queryKey: ["rating"] });
      queryClient.invalidateQueries({ queryKey: ["croquette"] });
    },
    onError: (error) => console.error("Create rating failed:", error),
  });

  return {
    createRating: postRatingMutation.mutate,
    isCreatingRating: postRatingMutation.isPending,
    ratingCreatingError: postRatingMutation.error,
  };
}

export function useUpdateRating(croquetteId: number) {
  const { ratingApi } = useApiEndpoints();
  const queryClient = useQueryClient();

  const putRatingMutation = useMutation({
    mutationFn: async (requestData: UpdateRatingRequest) =>
      await ratingApi.updateRating(requestData),
    onSuccess: () => {
      toast.dismiss();
      toast.success("Thanks for your rating. You are awesome!");
      queryClient.invalidateQueries({ queryKey: ["rating", croquetteId] });
      queryClient.invalidateQueries({ queryKey: ["croquette", croquetteId] });
    },
    onError: (error) => console.error("Update rating failed:", error),
  });

  return {
    updateRating: putRatingMutation.mutate,
    isUpdatingRating: putRatingMutation.isPending,
    ratingUpdatingError: putRatingMutation.error,
  };
}
