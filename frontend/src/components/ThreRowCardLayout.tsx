import React from "react";
import styled from "styled-components";
import CroquetteCardWrapper from "./croquetteCard/CroquetteCardWrapper";

const Grid = styled.div`
  display: grid;
`;

interface Croquette {
  id: number;
  content: React.ReactNode;
}

interface ThreeColumnCardLayoutProps {
  croquettes: Croquette[];
}

const ThreeColumnCardLayout: React.FC<ThreeColumnCardLayoutProps> = ({
  croquettes,
}) => {
  return (
    <Grid>
      {croquettes.map((croq) => (
        <CroquetteCardWrapper key={croq.id} croquetteId={croq.id}>
          {croq.content}
        </CroquetteCardWrapper>
      ))}
    </Grid>
  );
};

export default ThreeColumnCardLayout;
