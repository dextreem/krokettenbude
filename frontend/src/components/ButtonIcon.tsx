import styled from "styled-components";

const ButtonIcon = styled.button`
  background: none;
  border: none;
  /* padding: 0.6rem; */
  border-radius: var(--border-radius-sm);
  transition: all 0.2s;
  padding: 0.4rem;

  &:hover {
    background-color: var(--color-grey-400);
  }

  & svg {
    width: 2.2rem;
    height: 2.2rem;
    color: var(--color-grey-800);
  }

  &:disabled {
    cursor: not-allowed;
    opacity: 0.5;
  }
`;

export default ButtonIcon;
