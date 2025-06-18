import styled from "styled-components";
import type { CommentResponse } from "../../api-client";
import { getRelativeTime } from "../../utils/textUtils";
import { HiTrash } from "react-icons/hi2";
import useSessionState from "../../stores/SessionState";
import ButtonIcon from "../../components/ButtonIcon";
import { useDeleteComment } from "../../hooks/api/useCommentApi";
const StyledDiv = styled.div`
  display: grid;
  grid-template-columns: 15rem 15rem 1fr 4rem;
  gap: 1.2rem;
  border-bottom: 1px solid var(--color-grey-400);
  align-items: center;
`;

const UserDateDiv = styled.div`
  display: grid;
`;

const StyledSpan = styled.span`
  font-weight: bold;
`;

function CommentListItem({ commentItem }: { commentItem: CommentResponse }) {
  const userDetails = useSessionState((state) => state.userDetails);
  const { deleteComment } = useDeleteComment();

  const commentIsFromThisUser = commentItem.userId === userDetails?.id;

  return (
    <StyledDiv>
      <UserDateDiv>
        <StyledSpan>{commentItem.userName}</StyledSpan>
      </UserDateDiv>
      <span title={`on ${commentItem.createdAt}`}>
        {getRelativeTime(commentItem.createdAt)}
      </span>
      <span>{commentItem.comment}</span>
      {commentIsFromThisUser && (
        <ButtonIcon
          onClick={() => commentItem.id && deleteComment(commentItem.id)}
        >
          <HiTrash />
        </ButtonIcon>
      )}
    </StyledDiv>
  );
}

export default CommentListItem;
