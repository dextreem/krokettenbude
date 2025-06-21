import styled from "styled-components";

const Input = styled.input`
  border: 1px solid var(--color-grey-700);
  background-color: var(--color-grey-fixed-white);
  color: var(--color-grey-fixed-dark);
  border-radius: var(--border-radius-sm);
  padding: 0.8rem 1.2rem;
  box-shadow: var(--shadow-sm);
  width: 100%;

  &:disabled {
    background-color: var(--color-background-grey-opaque-fixed-light);
    cursor: not-allowed;
    opacity: 0.6;
    border: 1px solid var(--color-grey-700);
  }
`;

export default Input;
