import styled from "styled-components";

const StyledFooter = styled.footer`
  display: flex;
  align-items: center;
  justify-content: space-between;
  color: var(--color-grey-900);
  background-color: var(--color-brand-500);
  font-size: 1.4rem;
  padding: 0 2rem;
`;

function Footer() {
  return (
    <StyledFooter>
      <p>ver. {import.meta.env.VITE_VERSION}</p>
      <p>
        &copy;&nbsp;<span>Dominic 2025</span>
      </p>
      <p></p>
    </StyledFooter>
  );
}

export default Footer;
