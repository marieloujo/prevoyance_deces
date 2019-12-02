<?php

namespace App\Http\Resources\Marchand;

use App\Http\Resources\UserResources;
use App\Http\Resources\MessageResource;
use Illuminate\Http\Resources\Json\JsonResource;

class UserContactResource extends JsonResource
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
            'data' => new UsersResource($this),
            'messages' => MessageResource::collection($this->messages),
        ];
    }
}

