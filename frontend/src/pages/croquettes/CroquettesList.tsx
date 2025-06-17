import type { CroquetteResponse } from "../../api-client";
import croquettesData from "../../croquette_countries_refined.json";
import CroquetteCard from "../../components/croquetteCard/CroquetteCard";
import CroquetteCardWrapper from "../../components/croquetteCard/CroquetteCardWrapper";

const croquettes: CroquetteResponse[] = croquettesData;

function CroquettesList() {
  return (
    <div>
      <ul>
        {croquettes.map((c) => (
          <li key={c.id || `${c.name}-${c.country}`}>
            <CroquetteCardWrapper
              croquetteId={
                c.id || 0 //TODO: Fix that alternative
              }
            >
              <CroquetteCard limitDescription={true} croquetteData={c} />
            </CroquetteCardWrapper>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default CroquettesList;
