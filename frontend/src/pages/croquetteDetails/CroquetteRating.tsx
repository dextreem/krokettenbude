import styled from "styled-components";
import { HiStar } from "react-icons/hi";
import {
  useCreateRating,
  useGetUserRatingForCroquette,
  useUpdateRating,
} from "../../hooks/api/useRatingApi";
import Spinner from "../../components/Spinner";
import useSessionState from "../../stores/SessionState";
import { Link } from "react-router-dom";
import { ROUTES } from "../../utils/constants";

const Wrapper = styled.div`
  display: flex;
  gap: 0.4rem;
  margin: auto;
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

const SyledLink = styled(Link)`
  color: var(--color-brand-600);

  &:hover {
    color: var(--color-brand-500);
  }
`;

const ratingInWords = [
  "Awful",
  "Not delicious",
  "Not good, not bad",
  "Awesome",
  "A new prince 🦌 was born",
];

function CroquetteRating({ croquetteId }: { croquetteId: number }) {
  const userDetails = useSessionState((state) => state.userDetails);
  const isLoggedIn = userDetails ? true : false;
  const { rating, isRatingLoading } = useGetUserRatingForCroquette(croquetteId);
  const { createRating } = useCreateRating();
  const { updateRating } = useUpdateRating(croquetteId);

  if (isRatingLoading) return <Spinner />;

  const ratingValue = rating ? rating.rating : 0;

  function handleOnClickRating(newRating: number) {
    if (ratingValue > 0) {
      updateRating({
        ratingId: rating?.id || 0,
        ratingUpdateRequest: { rating: newRating },
      });
    } else {
      createRating({
        ratingCreateRequest: { rating: newRating, croquetteId },
      });
    }
  }

  return (
    <Wrapper>
      {isLoggedIn ? (
        <>
          <span>Rate this Croquette: </span>
          {[...Array(5)].map((_, i) => {
            const starIndex = i + 1;
            return (
              <StarIcon
                key={i}
                $filled={starIndex <= ratingValue}
                onClick={() => handleOnClickRating(starIndex)}
                title={ratingInWords[i]}
              />
            );
          })}
        </>
      ) : (
        <div>
          Please&nbsp;<SyledLink to={`/${ROUTES.SIGNUP}`}>sign up</SyledLink>
          &nbsp;or&nbsp;
          <SyledLink to={`/${ROUTES.LOGIN}`}>login</SyledLink>&nbsp;to interact
          with croquette lovers around the world!
        </div>
      )}
    </Wrapper>
  );
}

export default CroquetteRating;
