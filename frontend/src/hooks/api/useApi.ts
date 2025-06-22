import { useMemo } from "react";
import {
  CommentRestAPIEndpointsApi,
  Configuration,
  CroquetteRestAPIEndpointsApi,
  RatingRestAPIEndpointsApi,
  RecommendationControllerApi,
  UserRestAPIEndpointsApi,
} from "../../api-client";
import useSessionState from "../../stores/SessionState";

const basePath = import.meta.env.VITE_API_BASE_PATH || "";

export function useApiEndpoints() {
  const jwt = useSessionState((state) => state.token);

  console.log("Base Path:", basePath);

  const apiInstances = useMemo(() => {
    const config = new Configuration({
      basePath,
      accessToken: () => jwt ?? "",
    });

    return {
      userApi: new UserRestAPIEndpointsApi(config),
      recommendationApi: new RecommendationControllerApi(config),
      ratingApi: new RatingRestAPIEndpointsApi(config),
      croquetteApi: new CroquetteRestAPIEndpointsApi(config),
      commentApi: new CommentRestAPIEndpointsApi(config),
    };
  }, [jwt]); // re-render only if jwt changes

  return apiInstances;
}
