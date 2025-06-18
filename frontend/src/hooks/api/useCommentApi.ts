import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { useApiEndpoints } from "./useApi";
import type { AddCommentRequest } from "../../api-client";
import toast from "react-hot-toast";

export function useGetCommentsForCroquette(croquetteId: number) {
  const { commentApi } = useApiEndpoints();

  const { isLoading, data, error, refetch } = useQuery({
    queryKey: ["comments", croquetteId],
    queryFn: async () => await commentApi.retrieveAllComments({ croquetteId }),
  });

  return {
    isCommentsLoading: isLoading,
    comments: data,
    error,
    refetchComments: refetch,
  };
}

export function useCreateComment() {
  const { commentApi } = useApiEndpoints();
  const queryClient = useQueryClient();

  const postCommentMutation = useMutation({
    mutationFn: async (requestData: AddCommentRequest) =>
      await commentApi.addComment(requestData),
    onSuccess: () => {
      toast.dismiss();
      toast.success("Comment created.");
      queryClient.invalidateQueries({ queryKey: ["comments"] });
    },
    onError: (error) => console.error("Create comment failed:", error),
  });

  return {
    createComment: postCommentMutation.mutate,
    isCreatingComment: postCommentMutation.isPending,
    commentCreationError: postCommentMutation.error,
  };
}

export function useDeleteComment() {
  const { commentApi } = useApiEndpoints();
  const queryClient = useQueryClient();

  const deleteCommentMutation = useMutation({
    mutationFn: async (commentId: number) =>
      await commentApi.deleteComment({ commentId }),
    onSuccess: () => {
      toast.dismiss();
      toast.success("Comment successfully deleted!");
      queryClient.invalidateQueries({ queryKey: ["comments"] });
    },
    onError: (error) => {
      console.error("Delete comment failed:", error);
    },
  });

  return {
    deleteComment: deleteCommentMutation.mutate,
    isDeletingComment: deleteCommentMutation.isPending,
    deleteCommentError: deleteCommentMutation.error,
  };
}
