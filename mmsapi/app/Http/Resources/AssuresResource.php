<?php

namespace App\Http\Resources;

use Illuminate\Http\Resources\Json\JsonResource;
use App\Http\Resources\UsersResource;
class AssuresResource extends JsonResource
{
    /**
     * Transform the resource into an array.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return array
     */
    public function toArray($request)
    {
        return[
            'id' => $this->id,
            'profession' => $this->profession,
            'employeur' => $this->employeur,
            'user' => new UsersResource ($this->user),
        ];
    }
}
