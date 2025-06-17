import styled from "styled-components";

const StyledImg = styled.img`
  width: 128pt;
  height: 128pt;
  object-fit: cover;
  object-position: center;
  overflow: hidden;
`;

function CroquetteCardImage({
  imageUrl,
  name,
}: {
  imageUrl: string;
  name: string;
}) {
  return <StyledImg src={imageUrl} alt={`${name} Logo`} />;
}

export default CroquetteCardImage;
