<?php

namespace App\Http\Resources;

use App\Http\Resources\UserResource;
use Carbon\Carbon;
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
            'created_at' => $this->created_at,
            'updated_at' => Carbon::parse($this->updated_at)->diffForHumans(),
            'from_user_id' => new UserResource($this->from), // $this->from_user_id,
            
        ];
    }
}
