import styled from "styled-components";
import type { CommentResponse } from "../../api-client";
import Heading from "../../components/Heading";
import CommentListItem from "./CommentListItem";
import { useGetCommentsForCroquette } from "../../hooks/api/useCommentApi";
import Spinner from "../../components/Spinner";

const StyledDiv = styled.div`
  display: grid;
  gap: 1.2rem;
`;

const StyledUl = styled.ul`
  display: grid;
  gap: 1.2rem;
`;

function CommentList({ croquetteId }: { croquetteId: number }) {
  const { comments = [], isCommentsLoading } =
    useGetCommentsForCroquette(croquetteId);

  if (isCommentsLoading) return <Spinner />;

  return (
    <StyledDiv>
      <Heading as="h3">Comments ({comments.length}):</Heading>
      <StyledUl>
        {[...comments].reverse().map((c) => (
          <CommentListItem key={c.id} commentItem={c} />
        ))}
      </StyledUl>
    </StyledDiv>
  );
}

export default CommentList;
