import styled from "styled-components";
import Input from "../../components/Input";
import Button from "../../components/Button";
import Heading from "../../components/Heading";
import { useLogin, useSignup } from "../../hooks/api/useUserApi";
import { useState } from "react";

const StyledDiv = styled.div`
  margin: auto;
  padding-top: 2.4rem;
  padding-bottom: 1.2rem;
  max-width: 100rem;

  display: grid;
  gap: 1.2rem;
`;

const LoginSignup = ({ mode }: { mode: "login" | "signup" }) => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const { loginUser } = useLogin(email, password);
  const { signupUser } = useSignup(email, password);

  const isLogin = mode === "login";

  const handleLogin = () => {
    loginUser();
  };

  const handleSignup = async () => {
    signupUser();
  };

  return (
    <StyledDiv>
      <Heading as="h2">{isLogin ? "Login" : "Sign Up"}</Heading>
      <Input
        placeholder="Email Address"
        type="text"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
      />
      <Input
        placeholder="Password"
        type="password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />
      {isLogin ? (
        <Button onClick={handleLogin}>Login</Button>
      ) : (
        <Button onClick={handleSignup}>Signup</Button>
      )}
    </StyledDiv>
  );
};

export default LoginSignup;
