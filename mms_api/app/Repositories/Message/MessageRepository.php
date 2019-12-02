<?php

namespace App\Repositories\Message;
use App\Models\Message;
use App\Repositories\Message\Interfaces\MessageRepositoryInterface;

class MessageRepository implements MessageRepositoryInterface
{
    protected $message;

    public function __construct(Message $message)
    {
        $this->message = $message;
    }
    /**
     * Get a message by it's ID
     *
     * @param int
     * @return collection
     */

    public function getById($id)
    {
        return $this->message->findOrfail($id);
    }

    public function getAuthMessage($id)
    {
        return $this->message->where('from_user_id',$id)->where('notification',false)->orWhere('to_user_id',$id)->where('notification',false)->orderBy('created_at','desc')->get();
    }

    public function getAuthNotification($id)
    {
        return $this->message->where('from_user_id',$id)->where('notification',true)->orderBy('created_at','desc')->get();
    }

    /**
     * Get a message by it's ID
     *
     * @param string
     * @return collection
     */

    /**
     * Get's all assurés.
     *
     * @return mixed
     */
    public function all()
    {
        return $this->message->paginate();
    }

    /**
     * Delete a message.
     *
     * @param int
     */
    public function delete($id)
    {
        $this->message->findOrfail($id)->delete();
        
    }

    /**
     * Register a message.
     *
     * @param array
     */
    public function create(array $message_data)
    {
            $message = new Message();
            $message->body = $message_data['body'];
            $message->read_at = $message_data['read_at'];
            $message->to_user_id = $message_data['to_user_id'];
            $message->from_user_id = $message_data['from_user_id'];
            $message->notification = $message_data['notification'];
            
            $message->save();

            return $message;
    }

    /**
     * Update a message.
     *
     * @param int
     * @param array
     */
    public function update($id, array $message_data)
    {
            $message =  $this->message->findOrfail($id);
            $message->body = $message_data['body'];
            $message->read_at = $message_data['read_at'];
            $message->to_user_id = $message_data['to_user_id'];
            $message->from_user_id = $message_data['from_user_id'];
            $message->notification = $message_data['notification'];
            
            $message->update();
    }

}

