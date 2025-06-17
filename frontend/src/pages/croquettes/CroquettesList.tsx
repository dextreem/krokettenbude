// import type { CroquetteResponse } from "../../api-client";
// import croquettesData from "../../croquette_countries_refined.json";
import CroquetteCard from "../../components/croquetteCard/CroquetteCard";
import CroquetteCardWrapper from "../../components/croquetteCard/CroquetteCardWrapper";
import { useGetCroquettes } from "../../hooks/api/useCroquetteApi";
import Spinner from "../../components/Spinner";

// const croquettes: CroquetteResponse[] = croquettesData;

function CroquettesList() {
  const { croquettes, isCroquettesLoading } = useGetCroquettes();

  if (isCroquettesLoading) return <Spinner />;
  if (!croquettes) return <div>No croquettes found :(</div>;

  return (
    <div>
      <ul>
        {croquettes.map((c) => (
          <li key={c.id || `${c.name}-${c.country}`}>
            <CroquetteCardWrapper croquetteId={c.id || 0}>
              <CroquetteCard limitDescription={true} croquetteData={c} />
            </CroquetteCardWrapper>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default CroquettesList;
