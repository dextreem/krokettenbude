import React from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import { ROUTES } from "../../utils/constants";

const StyledDiv = styled.div`
  margin: 2.4rem 0;
  border: 1px solid var(--color-brand-100);
  border-radius: var(--border-radius-sm);

  display: flex;
  gap: 1.2rem;

  overflow: hidden;

  cursor: pointer;

  &:hover {
    background-color: var(--color-brand-100);
    border-color: var(--color-brand-200);
  }
`;

interface CroquetteCardWrapperProps {
  croquetteId: number;
  children: React.ReactNode;
}

const CroquetteCardWrapper: React.FC<CroquetteCardWrapperProps> = ({
  croquetteId,
  children,
}) => {
  const navigate = useNavigate();

  return (
    <StyledDiv
      onClick={() => navigate(`/${ROUTES.CROQUETTES}/${croquetteId}`)}
      data-croquette-id={croquetteId}
    >
      {children}
    </StyledDiv>
  );
};

export default CroquetteCardWrapper;
