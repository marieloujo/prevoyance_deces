<?php

namespace App\Http\Resources\Zone;

use App\Http\Resources\UsersResource;
use Illuminate\Http\Resources\Json\JsonResource;

class MarchandsCommuneResource extends JsonResource
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
            'commune' => UsersResource::collection($this->users),
        ];
    }
}
