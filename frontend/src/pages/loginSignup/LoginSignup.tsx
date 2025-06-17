import styled from "styled-components";
import Input from "../../components/Input";
import Button from "../../components/Button";

const StyledDiv = styled.div`
  margin: auto;
  padding-top: 2.4rem;
  padding-bottom: 1.2rem;
  max-width: 100rem;

  display: grid;
  gap: 1.2rem;
`;

const LoginSignup = ({ mode }: { mode: "login" | "signup" }) => {
  const isLogin = mode === "login";

  const handleLogin = () => {
    console.log("Logging in");
  };

  const handleSignup = () => {
    console.log("Signup");
  };

  return (
    <StyledDiv>
      <Input placeholder="Email Address" type="text" />
      <Input placeholder="Password" type="password" />
      {isLogin ? (
        <Button onClick={handleLogin}>Login</Button>
      ) : (
        <Button onClick={handleSignup}>Signup</Button>
      )}
    </StyledDiv>
  );
};

export default LoginSignup;
