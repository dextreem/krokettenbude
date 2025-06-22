import styled from "styled-components";
import { useNavigate } from "react-router-dom";
import { ROUTES } from "../../utils/constants";

const StyledLogo = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 1.2rem;
  cursor: pointer;
`;

const Logo = styled.img`
  height: 48px;
  border-left: 2px solid var(--color-brand-800);
  padding-left: 0.6rem;
  transform: scaleX(-1);
`;

const Span = styled.span`
  font-size: 2rem;
  font-weight: 500;
`;

function HeaderLogo() {
  const navigate = useNavigate();

  function onLogoClicked() {
    navigate(`/${ROUTES.CROQUETTES}`);
  }

  return (
    <StyledLogo onClick={onLogoClicked}>
      <Logo src="/ui/croqueteria_logo.png" alt="Croqueteria Logo" />
      <Span>Croqueteria</Span>
    </StyledLogo>
  );
}

export default HeaderLogo;
