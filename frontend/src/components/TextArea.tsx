import styled from "styled-components";

const TextArea = styled.textarea`
  border: 1px solid var(--color-grey-700);
  background-color: var(--color-grey-fixed-white);
  color: var(--color-grey-fixed-dark);
  border-radius: var(--border-radius-sm);
  padding: 0.8rem 1.2rem;
  box-shadow: var(--shadow-sm);
  width: 100%;
  resize: none;
`;

export default TextArea;
