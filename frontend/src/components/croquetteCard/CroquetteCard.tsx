import type { CroquetteResponse } from "../../api-client";
import CroquetteCardDetails from "./CroquetteCardDetails";
import CroquetteCardImage from "./CroquetteCardImage";

function CroquetteCard({
  croquetteData,
  limitDescription,
}: {
  croquetteData: CroquetteResponse;
  limitDescription?: boolean;
}) {
  return (
    <>
      <CroquetteCardImage
        imageUrl={croquetteData.imageUrl}
        name={croquetteData.name}
      />
      <CroquetteCardDetails
        limitDescription={limitDescription}
        croquetteData={croquetteData}
      />
    </>
  );
}

export default CroquetteCard;
