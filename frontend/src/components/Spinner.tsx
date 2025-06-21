import styled, { keyframes } from "styled-components";

// Keyframes for rotation animation
const rotate = keyframes`
  to {
    transform: rotate(1turn);
  }
`;

// Styled spinner with animation and gradient
const Spinner = styled.div`
  margin: 4.8rem auto;
  width: 4.4rem;
  aspect-ratio: 1;
  border-radius: 50%;

  background: radial-gradient(farthest-side, var(--color-brand-200) 94%, #0000)
      top/10px 10px no-repeat,
    conic-gradient(#0000 30%, var(--color-brand-fixed-medium));

  -webkit-mask: radial-gradient(farthest-side, #0000 calc(100% - 5px), #000 0);
  animation: ${rotate} 0.5s infinite linear;
`;

export default Spinner;
