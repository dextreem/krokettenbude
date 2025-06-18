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
import toast from "react-hot-toast";

export function useSignup(email: string, password: string) {
  const navigate = useNavigate();
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
      toast.dismiss();
      toast.success("User successfully created, please login!");
      console.log("User Created");
      navigate(`/${ROUTES.LOGIN}`);
    },
    onError: (error) => console.error("Create user failed:", error),
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
        setUserDetails({ email: data.email, role: data.role, id: data.id });
        navigate(`/${ROUTES.CROQUETTES}`);
        toast.dismiss();
        toast.success(`Hey ${data.email}. Nice to see you around!`);
      }
    },
    onError: (error) => {
      console.error(`Logging in user ${email} failed:`, error);
      toast.dismiss();
      toast.error("Error while logging in. Please check your credentials!");
    },
  });

  return {
    loginUser: postLoginMutation.mutate,
    isLoggingIn: postLoginMutation.isPending,
    loginError: postLoginMutation.error,
  };
}
