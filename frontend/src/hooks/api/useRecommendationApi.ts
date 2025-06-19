import { useMutation } from "@tanstack/react-query";
import {
  type RecommendCroquettesByTextRequest,
  type RecommendCroquettesRequest,
} from "../../api-client";
import { useApiEndpoints } from "./useApi";
import toast from "react-hot-toast";

export function useRecommendationByText() {
  const { recommendationApi } = useApiEndpoints();

  const postRecommendationMutation = useMutation({
    mutationFn: async (requestData: RecommendCroquettesByTextRequest) =>
      await recommendationApi.recommendCroquettesByText(requestData),
    onSuccess: (data) => {
      console.log(data);
    },
    onError: (error) => {
      toast.error("Error while recommending croquettes. Please try again.");
      console.error("Create user failed:", error);
    },
  });

  return {
    recommendCroquetteByText: postRecommendationMutation.mutate,
    isRecommendingByText: postRecommendationMutation.isPending,
    recommendingByTextError: postRecommendationMutation.error,
  };
}

export function useRecommendation() {
  const { recommendationApi } = useApiEndpoints();

  const postRecommendationMutation = useMutation({
    mutationFn: async (requestData: RecommendCroquettesRequest) =>
      await recommendationApi.recommendCroquettes(requestData),
    onSuccess: (data) => {
      console.log(data);
    },
    onError: (error) => {
      toast.error("Error while recommending croquettes. Please try again.");
      console.error("Create user failed:", error);
    },
  });
  return {
    recommendCroquette: postRecommendationMutation.mutate,
    isRecommending: postRecommendationMutation.isPending,
    recommendingError: postRecommendationMutation.error,
  };
}
