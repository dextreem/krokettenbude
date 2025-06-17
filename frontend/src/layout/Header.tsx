import styled from "styled-components";
import HeaderLogo from "./header/HeaderLogo";
import HeaderNav from "./header/HeaderNav";

const StyledHeader = styled.header`
  background: var(--color-brand-100);
  padding: 1.2rem 2.4rem;
  border-bottom: 1px solid var(--color-grey-200);

  display: flex;
  gap: 2.4rem;
  align-items: center;
  justify-content: space-between;
`;

function Header() {
  return (
    <StyledHeader>
      <HeaderLogo />
      <HeaderNav />
    </StyledHeader>
  );
}

export default Header;
