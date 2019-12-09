<?php

namespace App\Http\Resources\Collection;

use App\Http\Resources\Collection\ConversationResource;

use App\Http\Resources\Zone\UserResource;

use Illuminate\Http\Resources\Json\JsonResource;

class ConversationsUserResource extends JsonResource
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
            'read' => $this->read == 1 ? true : false ,
            'user' => new UserResource($this->user),
            'conversation' =>new ConversationResource($this->conversation),
        ];
    }
}
