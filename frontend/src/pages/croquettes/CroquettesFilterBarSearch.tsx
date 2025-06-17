import styled from "styled-components";
import Input from "../../components/Input";

const StyledInput = styled(Input)`
  width: 25rem;
  transition: width 0.3s ease;

  &:focus,
  &:hover {
    border-color: var(--color-brand-200);
    width: 35rem;
  }
`;

function CroquettesFilterBarSearch() {
  return <StyledInput type="text" id="search" placeholder="Search" />;
}

export default CroquettesFilterBarSearch;
