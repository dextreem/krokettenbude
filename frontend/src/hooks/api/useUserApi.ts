import { useMutation } from "@tanstack/react-query";
import {
  UserCreateRequestRoleEnum,
  type AddUserRequest,
  type LoginOperationRequest,
} from "../../api-client";
import { useApiEndpoints } from "./useApi";
import useSessionState from "../../stores/SessionState";
import { useNavigate } from "react-router-dom";
import { ROUTES } from "../../utils/constants";

export function useSignup(email: string, password: string) {
  const { userApi } = useApiEndpoints();

  const requestData: AddUserRequest = {
    userCreateRequest: {
      email,
      password,
      role: UserCreateRequestRoleEnum.Manager,
    },
  };

  const postLoginMutation = useMutation({
    mutationFn: async () => await userApi.addUser(requestData),
    onSuccess: () => {
      console.log("User Created");
    },
    onError: (error) => console.log("Create user failed:", error),
  });

  return {
    signupUser: postLoginMutation.mutate,
    isSigningIn: postLoginMutation.isPending,
    signupError: postLoginMutation.error,
  };
}

export function useLogin(email: string, password: string) {
  const setToken = useSessionState((state) => state.setToken);
  const setUserDetails = useSessionState((state) => state.setUserDetails);
  const navigate = useNavigate();
  const { userApi } = useApiEndpoints();

  const requestData: LoginOperationRequest = {
    loginRequest: { email, password },
  };

  const postLoginMutation = useMutation({
    mutationFn: async () => await userApi.login(requestData),
    onSuccess: (data) => {
      const token = data.token;
      if (token) {
        setToken(token);
        setUserDetails({ email });
        navigate(`/${ROUTES.CROQUETTES}`);
      }
    },
    onError: (error) => console.log("Create user failed:", error),
  });

  return {
    loginUser: postLoginMutation.mutate,
    isLoggingIn: postLoginMutation.isPending,
    loginError: postLoginMutation.error,
  };
}
