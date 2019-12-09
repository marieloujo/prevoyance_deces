<?php

namespace App\Repositories\ConversationUser;

use App\Models\ConversationUser;
use App\Repositories\ConversationUser\Interfaces\ConversationUserRepositoryInterface;

class ConversationUserRepository implements ConversationUserRepositoryInterface
{
    protected $conversationUser;

    public function __construct(ConversationUser $conversationUser)
    {
        $this->conversationUser = $conversationUser;
    }
    /**
     * Get a conversationUser by it's ID
     *
     * @param int
     * @return collection
     */

    public function getById($id)
    {
        return $this->conversationUser->findOrfail($id);
    }

    /**
     * Get a conversationUser by it's ID
     *
     * @param string
     * @return collection
     */

    /**
     * Get's all conversationUsers.
     *
     * @return mixed
     */
    public function all()
    {
        return $this->conversationUser->paginate();
    }

    /**
     * Delete a conversationUser.
     *
     * @param int
     */
    public function delete($id)
    {
        $this->conversationUser->findOrfail($id)->delete();
        
    }

    /**
     * Register a conversationUser.
     *
     * @param array
     */
    public function create(array $conversationUser_data)
    {
            $conversationUser = new ConversationUser();
            $conversationUser->read = $conversationUser_data['read'];
            $conversationUser->conversation_id = $conversationUser_data['conversation_id'];
            $conversationUser->user_id = $conversationUser_data['user_id'];
            $conversationUser->save();

            return $conversationUser;
    }

    /**
     * Update a benefice.
     *
     * @param int
     * @param array
     */
    public function update($id, array $conversationUser_data)
    {
            $conversationUser =  $this->conversationUser->findOrfail($id);
            $conversationUser->read = $conversationUser_data['read'];
            $conversationUser->conversation_id = $conversationUser_data['conversation_id'];
            $conversationUser->user_id = $conversationUser_data['user_id'];
            $conversationUser->update();
    }

}

