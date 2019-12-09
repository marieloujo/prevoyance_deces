<?php

namespace App\Repositories\Conversation\Interfaces;

interface ConversationRepositoryInterface
{
    
    /**
     * Get's all conversations.
     *
     * @return mixed
     */
    public function all();

    /**
     * Deletes an conversation.
     *
     * @param int
     */
    public function delete($id);

    /**
     * Get's an conversation by it's ID
     *
     * @param int
     */
    public function getById($id);


    /**
     * Create a conversation.
     *
     * @param array
     */
    public function create();

    /**
     * Update an conversation.
     *
     * @param int
     * @param array
     */
    public function update($id, array $conversation_data);


}