<?php

namespace App\Http\Resources\Conversation;

use App\Http\Resources\UserResource;
use Illuminate\Http\Resources\Json\JsonResource;

class MessageResource extends JsonResource
{
    /**
     * Transform the resource into an array.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return array
     */
    public function toArray($request)
    {
        return [
            'id' => $this->id,
            'body' => $this->body,
            'notification' => $this->notification == 1 ? true : false ,
            'read_at' => $this->read_at,
            'from_user_id' => new UserResource($this->from), // $this->from_user_id,
            'to_user_id' => $this->to_user_id,
        ];
    }
}
