<?php

namespace App\Http\Resources\Collection;

use App\Http\Resources\Zone\UserResource;
use Illuminate\Http\Resources\Json\JsonResource;

class NotificationsUserResource extends JsonResource
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
            'conversation' =>[
                'id' => $this->conversation->id,            
                'messages' => MessageResource::collection($this->conversation->messages()->where('notification',true)->orderByDesc('created_at')->paginate(5)),
            ]
        ];
    }
}
