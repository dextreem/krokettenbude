import React, { useState } from "react";
import styled from "styled-components";
import { HiStar } from "react-icons/hi";

const Wrapper = styled.div`
  display: flex;
  gap: 0.4rem;
  align-items: center;
  margin-left: auto;
`;

const StarIcon = styled(HiStar)<{ $filled: boolean }>`
  width: 2.5rem;
  height: 2.5rem;
  color: ${({ $filled }) => ($filled ? "#FFD700" : "#ccc")};
  cursor: pointer;
  transition: transform 0.1s;

  &:hover {
    transform: scale(1.2);
  }
`;

const ratingInWords = [
  "Awful",
  "Not delicious",
  "Not good, not bad",
  "Awesome",
  "A new prince 🦌 was born",
];

const CroquetteRating: React.FC = () => {
  const [rating, setRating] = useState<number>(0); // TODO: Replace by API call

  return (
    <Wrapper>
      <span>Rate this Croquette: </span>
      {[...Array(5)].map((_, i) => {
        const starIndex = i + 1;
        return (
          <StarIcon
            key={i}
            $filled={starIndex <= rating}
            onClick={() => setRating(starIndex)}
            title={ratingInWords[i]}
          />
        );
      })}
    </Wrapper>
  );
};

export default CroquetteRating;
